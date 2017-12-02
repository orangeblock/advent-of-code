use std::fs::File;
use std::io::prelude::*;
use std::collections::HashMap;

fn manhattan_distance(x: (i32, i32), y: (i32, i32)) -> i32 {
    (x.0 - y.0).abs() + (x.1 - y.1).abs()
}

fn modulo(x: i32, m: i32) -> i32 {
    ((x % m) + m) % m
}

fn make_move(position: (i32, i32, i32), direction: &(char, i32)) -> Vec<(i32, i32, i32)> {
    match match direction.0 {
        'R' => modulo((position.2 + 1), 4),
        'L' => modulo((position.2 - 1), 4),
        _   => panic!("Invalid turn")
    }{
        // North
        0 => (position.1+1..position.1+direction.1+1).map(|i| (position.0, i, 0)).collect(),
        // East
        1 => (position.0+1..position.0+direction.1+1).map(|i| (i, position.1, 1)).collect(), 
        // South
        2 => (position.1-direction.1..position.1).map(|i| (position.0, i, 2)).rev().collect(),
        // West
        3 => (position.0-direction.1..position.0).map(|i| (i, position.1, 3)).rev().collect(),
        _ => panic!("Invalid direction")
    }
}

fn move_tuples() -> Vec<(char, i32)> {
    let mut file = File::open("p1/input.txt").expect("File not found");
    let mut data = String::new();
    file.read_to_string(&mut data).expect("Error reading file");
    data.trim()
        .split(", ")
        .map(|x| 
            ( x.chars().nth(0).expect("Can't parse turn"), 
              (&x.chars().as_str()[1..]).parse().expect("Can't parse steps"))
        ).collect()
}

pub fn part_one() -> i32 {
    let mut state = (0, 0, 0);
    for tuple in move_tuples().iter() {
        let moves = make_move(state, tuple);
        state = *moves.last().expect("No moves!!!");
    }
    manhattan_distance((0, 0), (state.0, state.1))
}

pub fn part_two() -> i32 {
    let mut positions: HashMap<(i32, i32), i32> = HashMap::new();
    let mut state = (0, 0, 0);
    for tuple in move_tuples().iter() {
        let moves = make_move(state, tuple);
        for m in moves.iter() {
            match positions.get(&(m.0, m.1)) {
                Some(_) => return manhattan_distance((0, 0), (m.0, m.1)),
                None => { positions.insert((m.0, m.1), 1); }
            }
        }
        state = *moves.last().expect("No moves!!!");
    }
    panic!("No re-visited point!");
}
