# GraphLib

Library provides some basic operations on directed and undirected graphs:
* addVertex - adds vertex to the graph;
* addEdge - adds edge to the graph;
* getPath - returns a list of edges between 2 vertices (path isn't optimal because uses simple DFS algorithm);
* Vertices should be of a user defined type.

Both vertices and edges classes could be customized. [See example](./src/test/java/com/example/ussage/model)