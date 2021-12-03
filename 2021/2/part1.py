tups =  map(
    lambda t: (t[0], int(t[1])),
    (line.strip().split(' ') for line in open('input.txt', 'r'))
)

pos = depth = 0
for (action, val) in tups:
    if action == 'forward':
        pos += val
    elif action == 'up':
        depth -= val
    elif action == 'down':
        depth += val

print(pos * depth)
