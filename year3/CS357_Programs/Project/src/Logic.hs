--Tomas Salaz 
--2048 Game Logic
module Logic
  ( initState,     -- Initialization
    slideAndMerge, -- Operations
    move,          -- Moves & Spawning
    spawnRandom,
    has2048,       -- Game‐Over Check
    movesAvailable, 
    Direction(..)
  ) where

import Types
import System.Random --(StdGen, split, randomR)
import Data.List     --(transpose)
import Data.List (reverse, transpose)

-- Direction Enums
data Direction = GoLeft | GoRight | GoUp | GoDown
  deriving (Eq, Show)

-- Initial Board State
emptyBoard :: Int -> Board
emptyBoard n = replicate n (replicate n Nothing)

-- Random Spawn 
spawnRandom :: Board -> StdGen -> (Board, StdGen)
spawnRandom b gen0 = case empties of
  [] -> (b, gen0)
  _  -> let (i, g1) = randomR (0, length empties-1) gen0 --Rand Index of Empty
            (r, c)  = empties !! i                       --Location of Rand 
            (roll, g2) = randomR (1 :: Int, 10) g1       --Number Gen, 2=90%, 4=10%
            val = if roll == 1 then 4 else 2
            --val = if roll == 1 then 4 else 1024

            --Make New Board and get New Random to Return
            newR = take c (b !! r) ++ [Just val] ++ drop (c+1) (b!!r)
            newBoard = take r b ++ [newR] ++ drop (r+1) b 
        in (newBoard, g2)
  where 
    --Algo for finding empties
    empties = [ (i,j) | (i, row) <- zip [0..] b,
                        (j, cell) <- zip [0..] row,
                         cell == Nothing]

-- Start a new Game with 2 Random Spawns
initState :: StdGen -> GameState
initState gen0 = let
      size = 4
      b0 = emptyBoard size
      (b1,g1) = spawnRandom b0 gen0
      (b2, g2) = spawnRandom b1 g1
  in GameState b2 0 g2 Playing

-- Moves Tiles and Merges if needed and Scoring 
slideAndMerge :: [Cell] -> ([Cell], Int)
slideAndMerge row = let
      vs = [v | Just v <- row]     --Tiles with Values
      go [ ] =  ([ ], 0)           --Recursive Call, Base Case
      go [x] =  ([x], 0)           --Recursive Call, Only one Tile, no Merge Possible
      go (x:y:zs)                  --Merge Call
        | x == y =    let (rest, pts) = go zs in (x*2 : rest, x*2 + pts)
        | otherwise = let (rest, pts) = go (y:zs) in (x : rest, pts)
      (mergerd, pts) = go vs
      --Padded Restores the Grip to have Notings in the necessary cells
      padded = map Just mergerd ++ replicate (length row - length mergerd) Nothing
      in (padded, pts)


-- Presps the board for making a move, Uses Transposition to Orient the Board Correctly
move :: Direction -> Board -> (Board, Int)
move dir b = case dir of
  GoLeft  -> go id               id                b
  GoRight -> go (map reverse)    (map reverse)     b
  GoUp    -> go transpose        transpose         b
  GoDown  -> go (map reverse . transpose)
                (map reverse . transpose)          b
  where
    go prep undo board =
      let rows    = prep board
          result  = map slideAndMerge rows
          newRows = map fst result
          pts     = sum (map snd result)
      in (undo newRows, pts)

-- EndGaame Checks
has2048 :: Board -> Bool
has2048 = any (== Just 2048).concat

movesAvailable :: Board -> Bool
movesAvailable b = any (== Nothing) cells || any canMerge rows 
                                          || any canMerge cols
  where
    cells = concat b 
    rows = b 
    cols = transpose b 
    canMerge xs = any (\(a,b) -> a == b && a /= Nothing) (zip xs (tail xs))
