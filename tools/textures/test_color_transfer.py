from __future__ import annotations

import unittest

from color_transfer import transfer_palette


class TransferPaletteTest(unittest.TestCase):
    def test_transfers_full_ranked_palette_and_preserves_alpha(self) -> None:
        base = [
            (0, 0, 0, 0),
            (20, 20, 20, 64),
            (80, 80, 80, 128),
            (140, 140, 140, 192),
            (220, 220, 220, 255),
        ]
        source = [
            (70, 10, 5, 255),
            (120, 30, 10, 255),
            (200, 80, 20, 255),
            (255, 220, 90, 255),
        ]

        output = transfer_palette(base, source)

        self.assertEqual(output[0], (0, 0, 0, 0))
        self.assertEqual([pixel[:3] for pixel in output[1:]], [pixel[:3] for pixel in source])
        self.assertEqual([pixel[3] for pixel in output[1:]], [64, 128, 192, 255])

    def test_maps_equal_base_luminance_to_the_same_middle_rank(self) -> None:
        base = [(100, 100, 100, 255), (100, 100, 100, 96)]
        source = [(10, 10, 10, 255), (240, 240, 240, 255)]

        output = transfer_palette(base, source)

        self.assertEqual(output[0][:3], (125, 125, 125))
        self.assertEqual(output[1][:3], (125, 125, 125))
        self.assertEqual([pixel[3] for pixel in output], [255, 96])

    def test_excludes_hidden_pixels_from_rank_calculation(self) -> None:
        base = [(20, 20, 20, 255), (220, 220, 220, 255)]
        source = [(40, 50, 60, 255), (200, 210, 220, 255)]

        output = transfer_palette(base, source, {1})

        self.assertEqual(output[0], (120, 130, 140, 255))
        self.assertEqual(output[1], (0, 0, 0, 0))

    def test_rank_scale_reduces_output_contrast(self) -> None:
        base = [(0, 0, 0, 255), (255, 255, 255, 255)]
        source = [(0, 0, 0, 255), (100, 100, 100, 255), (200, 200, 200, 255)]

        output = transfer_palette(base, source, rank_scale=0.5)

        self.assertEqual(output[0], (50, 50, 50, 255))
        self.assertEqual(output[1], (150, 150, 150, 255))


if __name__ == "__main__":
    unittest.main()
