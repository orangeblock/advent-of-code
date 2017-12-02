use std::fs::File;
use std::io::prelude::*;

fn instructions() -> Vec<Vec<char>> {
    let mut f = File::open("p2/input.txt").expect("File not found");
    let mut data = String::new();
    f.read_to_string(&mut data).expect("Error reading file");
    data.lines().map(|line| line.chars().collect()).collect()
}

fn is_valid(tup: (i32, i32), shape: &Vec<Vec<Option<char>>>) -> bool {
    if tup.0 >= 0 && tup.1 >= 0 && 
       tup.0 < shape.len() as i32 && tup.1 < shape[tup.0 as usize].len() as i32 {
        return match shape[tup.0 as usize][tup.1 as usize] {
            Some(_) => true,
            None    => false
        }
    }
    false
}

fn solve(instructions: &Vec<Vec<char>>, shape: &Vec<Vec<Option<char>>>, start: (i32, i32)) -> String {
    let mut code = String::new();
    for instruction in instructions.iter() {
        let mut state = start;
        for c in instruction.iter() {
            let candidate = match c {
                &'U' => (state.0-1, state.1),
                &'D' => (state.0+1, state.1),
                &'L' => (state.0, state.1-1),
                &'R' => (state.0, state.1+1),
                _    => panic!("Invalid direction")
            };
            if is_valid(candidate, shape) {
                state = candidate;
            }
        }
        code += &shape[state.0 as usize][state.1 as usize]
                    .expect("Invalid state detected").to_string();
    }
    code
}

pub fn part_one() -> String {
    let shape = vec![
        vec![Some('1'), Some('2'), Some('3')],
        vec![Some('4'), Some('5'), Some('6')],
        vec![Some('7'), Some('8'), Some('9')]
    ];
    solve(&instructions(), &shape, (1,1))
}

pub fn part_two() -> String {
    let shape = vec![
        vec![None, None, Some('1'), None, None],
        vec![None, Some('2'), Some('3'), Some('4'), None],
        vec![Some('5'), Some('6'), Some('7'), Some('8'), Some('9')],
        vec![None, Some('A'), Some('B'), Some('C'), None],
        vec![None, None, Some('D'), None, None],
    ];
    solve(&instructions(), &shape, (2,0))
}
