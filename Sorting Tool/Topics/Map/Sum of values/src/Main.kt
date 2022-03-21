fun summator(map: Map<Int, Int>) = map.filterKeys { it % 2 == 0 }.values.sum()
