xs = list(map(int, open('input.txt', 'r').read().split(',')))

m = { i : 0 for i in range(9) }
for x in xs:
    m[x] += 1

for _ in range(256):
    births = m[0]
    m = { i : m[i+1] for i in range(8) }
    m[6] += births
    m[8] = births

print(sum(m[i] for i in range(9)))
