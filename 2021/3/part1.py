def strbin2dec(s):
    """Converts a string representing a binary number to decimal"""
    d = 0
    for i in range(len(s)):
        d += (2 ** i) * int(s[len(s)-1-i])
    return d

def vecadd(a, b):
    return list(map(sum, zip(a, b)))

# read input into a list of vectors
vecs = [list(map(int, line.strip())) for line in open('input.txt', 'r')]

# generate a vector containing the number of 1s in each position (sum of input vectors)
vec_total = [0] * len(vecs[0])
for vec in vecs:
    vec_total = vecadd(vec, vec_total)

# calculate gamma by finding most common bit in each position
midpoint = len(vecs) / 2.0
gamma = list(map(lambda x : 1 if x >= midpoint else 0, vec_total))
# epsilon is the inverse of gamma
epsilon = list(map(lambda x : x ^ 1, gamma))

# convert to decimal and print result
gamma_str = ''.join(map(str, gamma))
epsilon_str = ''.join(map(str, epsilon))
print(strbin2dec(gamma_str) * strbin2dec(epsilon_str))
