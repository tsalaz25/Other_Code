--Tomas Salaz
--Types used in Game 
module Types
  (Cell,          -- Cell & Board
   Board,
   Status(..),    -- Game status    
   GameState(..) -- Full game state
  ) where

import System.Random (StdGen)

type Cell = Maybe Int
type Board = [[Cell]]

data Status = Playing | Won | Lost
  deriving (Eq, Show)

data GameState = GameState
  { board  :: Board,    -- current tile layout
    score  :: Int,      -- accumulated points
    gen    :: StdGen,   -- RNG for new‐tile placement
    status :: Status    -- game‐over flags
  }
  deriving (Show)