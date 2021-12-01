depths = [int(line.strip()) for line in open('input.txt', 'r')]

incs = 0
for i in range(1, len(depths)-2):
    if sum(depths[i:i+3]) > sum(depths[i-1:i+2]):
        incs += 1

print(incs)
