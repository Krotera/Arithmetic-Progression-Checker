# Arithmetic Progression Checker

**v2.0.0** (May have critical bugs)

## Background
This program

- takes a set of sequential, contiguous integers `[n, n + 1, ..., k - 1, k]`,
- splits the set into palindromic partitions,
- checks the two subsets of each for two arithmetic progressions (series of terms of the form `a + xd`, with `x =
0, 1, 2, ..., p` for the first subset and `0, 1, 2, ..., q` for the second subset) for two integers `p` and `q`,
- and returns the partitions whose subsets were not found to contain those arithmetic progressions.

## License

GNU General Public License v3.0