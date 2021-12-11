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

def points_between(a, b):
    xdiff = b[0] - a[0]
    ydiff = b[1] - a[1]
    dist = max(abs(xdiff), abs(ydiff))
    if xdiff == 0:
        xs = [a[0]] * (dist + 1)
    else:
        xs = list(range(a[0], b[0], int(xdiff/abs(xdiff)))) + [b[0]]
    if ydiff == 0:
        ys = [a[1]] * (dist + 1)
    else:
        ys = list(range(a[1], b[1], int(ydiff/abs(ydiff)))) + [b[1]]
    return list(zip(xs, ys))

grid = [[0] * (max_x + 1) for _ in range((max_y + 1))]
for s, e in lines:
    for x, y in points_between(s, e):
        grid[y][x] += 1

total = 0
for row in grid:
    for x in row:
        if x > 1:
            total += 1
print(total)
