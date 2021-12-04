class Grid(object):
    def __init__(self, dimension, grid_vec):
        if len(grid_vec) != dimension ** 2:
            raise Exception(
                'invalid dimensions, expecting %dx%d' % (dimension, dimension)
            )
        self.dim = dimension
        self.vec = grid_vec
        self.elem_mask = [0] * len(grid_vec)
        self.group_mask = [0] * (2 * dimension)
        self.idx = {
            grid_vec[i] : (i // dimension, i % dimension)
            for i in range(len(grid_vec))
        }

    def mark_element(self, elem):
        if elem not in self.idx:
            return False
        r, c = self.idx[elem]
        if(self.elem_mask[r * self.dim + c] == 0):
            self.elem_mask[r * self.dim + c] = 1
            self.group_mask[r] += 1
            self.group_mask[self.dim + c] += 1
        return self.group_mask[r] == self.dim or \
               self.group_mask[self.dim + c] == self.dim

    def unmarked(self):
        return list(map(
            lambda i : self.vec[i],
            filter(lambda i : self.elem_mask[i] == 0,
                   range(len(self.vec))
            )
        ))

# parse number ordering and grids
grids = []
with open('input.txt', 'r') as f:
    current_grid = []
    for i, line in enumerate(f):
        if i == 0:
            nums = list(map(int, line.split(',')))
        else:
            line = line.strip()
            if line:
                current_grid.extend(
                    map(int, filter(lambda x: x, line.split(' ')))
                )
                if len(current_grid) == 25:
                    grids.append(Grid(5, current_grid))
                    current_grid = []

# progressively mark elements and generate the sequence of winning boards
grid_scores = {}
win_sequence = []
for num in nums:
    for grid in grids:
        if grid.mark_element(num) and grid not in grid_scores:
            grid_scores[grid] = sum(grid.unmarked()) * num
            win_sequence.append(grid)

print("First winning board: %d" % grid_scores[win_sequence[0]])
print("Last winning board: %d" % grid_scores[win_sequence[-1]])
