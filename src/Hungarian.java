

// 递归看是否能找到一个匹配的对象

private boolean find(int[][] graph, int[] match, boolean[] visited, int part1) {
	
	for (int next : graph[part1]) {
		
		if (!visited[next]) {
			visited[next] = true;
			// if the next one is available or, we can find another available.
			if (match[next] == -1 || find(graph, match, visited, match[next])) {
				match[next] = part1;
				return true;
			}
		}
	}
	
	return false;
}