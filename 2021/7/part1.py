xs = list(map(int, open('input.txt', 'r').read().split(',')))
print(min(map(
    sum,
    [ [abs(x-i) for x in xs] for i in range(min(xs), max(xs)+1) ]
)))
