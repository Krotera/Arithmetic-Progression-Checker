# Arithmetic Progression Checker
**v2.4.0**
***
*For Tom, with love~*
***
## Background
This program

- takes a set of sequential, contiguous integers `[n, n + 1, ..., k - 1, k]`,
- splits the set into palindromic partitions,
- checks the two subsets of each for two arithmetic progressions (series of terms of the form `a + xd`, with `x =
0, 1, 2, ..., p` for the first subset and `0, 1, 2, ..., q` for the second subset) for two integers `p` and `q`,
- and returns all partitions whose subsets were not found to contain those arithmetic progressions (in 'mode 1') or just the first such partition ('mode 2'), which is faster.

## License

[GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.html)