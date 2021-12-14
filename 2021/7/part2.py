xs = list(map(int, open('input.txt', 'r').read().split(',')))

def triag(n):
    return (n * (n + 1)) // 2

print(min(map(
    sum,
    [ [triag(abs(x-i)) for x in xs] for i in range(min(xs), max(xs)+1) ]
)))
