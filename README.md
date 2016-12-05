# Arithmetic Progression Checker
***
*For Tom, with love~*
***

**v2.2.1**

NOTE: May exhibit memory leak errors (e.g., "GC overhead limits exceeded" or "Java heap space") with many consecutive runs or on certain runs depending on machine

## Background
This program

- takes a set of sequential, contiguous integers `[n, n + 1, ..., k - 1, k]`,
- splits the set into palindromic partitions,
- checks the two subsets of each for two arithmetic progressions (series of terms of the form `a + xd`, with `x =
0, 1, 2, ..., p` for the first subset and `0, 1, 2, ..., q` for the second subset) for two integers `p` and `q`,
- and returns the partitions whose subsets were not found to contain those arithmetic progressions.

## License

[GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.html)