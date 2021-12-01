depths = [int(line.strip()) for line in open('input.txt', 'r')]

incs = 0
for i in range(1, len(depths)):
    if depths[i] > depths[i-1]:
        incs += 1

print(incs)
