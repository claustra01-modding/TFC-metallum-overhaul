from __future__ import annotations

from bisect import bisect_left, bisect_right
from io import BytesIO
from pathlib import Path
from zipfile import ZipFile

from PIL import Image


Pixel = tuple[int, int, int, int]


def load_png(path: Path) -> tuple[tuple[int, int], list[Pixel]]:
    with Image.open(path) as image:
        rgba = image.convert("RGBA")
        return rgba.size, list(rgba.get_flattened_data())


def load_zip_png(archive: Path, member: str) -> tuple[tuple[int, int], list[Pixel]]:
    with ZipFile(archive) as jar:
        data = jar.read(member)
    with Image.open(BytesIO(data)) as image:
        rgba = image.convert("RGBA")
        return rgba.size, list(rgba.get_flattened_data())


def save_png(path: Path, size: tuple[int, int], pixels: list[Pixel]) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    image = Image.new("RGBA", size)
    image.putdata(pixels)
    image.save(path, optimize=True)


def flip_horizontal(size: tuple[int, int], pixels: list[Pixel]) -> list[Pixel]:
    width, height = size
    return [
        pixels[y * width + (width - 1 - x)]
        for y in range(height)
        for x in range(width)
    ]


def luminance(pixel: Pixel) -> float:
    red, green, blue, _ = pixel
    return (0.2126 * red + 0.7152 * green + 0.0722 * blue) / 255


def lerp(first: tuple[int, int, int], second: tuple[int, int, int], amount: float) -> tuple[int, int, int]:
    return tuple(round(first[index] + (second[index] - first[index]) * amount) for index in range(3))


def sample_palette(source: list[Pixel], percentile: float) -> tuple[int, int, int]:
    position = percentile * (len(source) - 1)
    lower = int(position)
    upper = min(lower + 1, len(source) - 1)
    return lerp(source[lower][:3], source[upper][:3], position - lower)


def transfer_palette(
    base_pixels: list[Pixel],
    source_pixels: list[Pixel],
    hidden_indices: set[int] | None = None,
    rank_scale: float = 1.0,
) -> list[Pixel]:
    if not 0 < rank_scale <= 1:
        raise ValueError("rank_scale must be greater than 0 and at most 1")

    hidden = hidden_indices or set()
    source = sorted(
        (pixel for pixel in source_pixels if pixel[3] > 0),
        key=lambda pixel: (luminance(pixel), pixel[0], pixel[1], pixel[2]),
    )
    if not source:
        raise ValueError("source texture has no visible pixels")

    visible_luminances = sorted(
        luminance(pixel)
        for index, pixel in enumerate(base_pixels)
        if pixel[3] > 0 and index not in hidden
    )
    if not visible_luminances:
        raise ValueError("base texture has no visible pixels")

    last = len(visible_luminances) - 1
    output: list[Pixel] = []
    for index, pixel in enumerate(base_pixels):
        if pixel[3] == 0 or index in hidden:
            output.append((0, 0, 0, 0))
            continue
        value = luminance(pixel)
        if last == 0:
            percentile = 0.5
        else:
            lower = bisect_left(visible_luminances, value)
            upper = bisect_right(visible_luminances, value) - 1
            percentile = ((lower + upper) * 0.5) / last
        percentile = 0.5 + (percentile - 0.5) * rank_scale
        output.append((*sample_palette(source, percentile), pixel[3]))
    return output
