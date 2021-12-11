xs = list(map(int, open('input.txt', 'r').read().split(',')))

for _ in range(80):
    births = 0
    for i, x in enumerate(xs):
        if x == 0:
            births += 1
            xs[i] = 6
        else:
            xs[i] -= 1
    xs.extend([8] * births)

print(len(xs))
