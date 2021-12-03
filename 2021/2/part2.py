tups =  map(
    lambda t: (t[0], int(t[1])),
    (line.strip().split(' ') for line in open('input.txt', 'r'))
)

pos = depth = aim = 0
for (action, val) in tups:
    if action == 'forward':
        pos += val
        depth += aim * val
    elif action == 'up':
        aim -= val
    elif action == 'down':
        aim += val

print(pos * depth)
