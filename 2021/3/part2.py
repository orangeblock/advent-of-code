class Rating:
    OXYGEN = 1
    CO2 = 2

def strbin2dec(s):
    """Converts a string representing a binary number to decimal"""
    d = 0
    for i in range(len(s)):
        d += (2 ** i) * int(s[len(s)-1-i])
    return d

def vecadd(a, b):
    return list(map(sum, zip(a, b)))

def vecsum(vecs):
    vec_total = [0] * len(vecs[0])
    for vec in vecs:
        vec_total = vecadd(vec, vec_total)
    return vec_total

def get_rating_vector(rating_type, vecs):
    """
    Finds the vector representing the required type by progressively eliminating
    vectors that don't match the rating's criteria.

    Throws an exception if the vector cannot be found.
    """
    vs = vecs[:]
    curr_idx = 0
    while len(vs) > 1 and curr_idx < len(vs[0]):
        vsum = vecsum(vs)
        curr_idx_midpoint = len(vs) / 2.0
        if rating_type == Rating.OXYGEN:
            curr_idx_target = 1 if vsum[curr_idx] >= curr_idx_midpoint else 0
        else:
            curr_idx_target = 0 if vsum[curr_idx] >= curr_idx_midpoint else 1
        # eliminate vectors that don't have the required number in current position
        vs = list(filter(lambda x : x[curr_idx] == curr_idx_target, vs))
        curr_idx += 1
    if len(vs) != 1:
        raise Exception('could not find target vector')
    return vs[0]

vecs = [list(map(int, line.strip())) for line in open('input.txt', 'r')]

oxy_rating = get_rating_vector(Rating.OXYGEN, vecs)
co2_rating = get_rating_vector(Rating.CO2, vecs)

oxy_str = ''.join(map(str, oxy_rating))
co2_str = ''.join(map(str, co2_rating))
print(strbin2dec(oxy_str) * strbin2dec(co2_rating))
