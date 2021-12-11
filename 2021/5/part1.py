lines = []
max_x = max_y = 0
with open('input.txt', 'r') as f:
    for line_str in f:
        line = []
        for point_str in line_str.split('->'):
            tup = tuple(map(int, point_str.strip().split(',')))
            if(tup[0] > max_x): max_x = tup[0]
            if(tup[1] > max_y): max_y = tup[1]
            line.append(tup)
        lines.append(line)

grid = [[0] * (max_x + 1) for _ in range((max_y + 1))]
for s, e in lines:
    if s[0] == e[0]:
        for i in range(min(s[1], e[1]), max(s[1], e[1]) + 1):
            grid[i][s[0]] += 1
    elif s[1] == e[1]:
        for i in range(min(s[0], e[0]), max(s[0], e[0]) + 1):
            grid[s[1]][i] += 1

total = 0
for row in grid:
    for x in row:
        if x > 1:
            total += 1
print(total)
