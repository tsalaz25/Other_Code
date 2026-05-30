-- Tomas Salaz
-- Graphics using Brillo
{-# LANGUAGE LambdaCase #-}
module Graphics
  ( drawState,
    handleEvent
  ) where

import Brillo
import Brillo.Data.Color    (Color, white, black, green, red, greyN)
import Brillo.Data.Picture  (Picture, blank, rectangleSolid, translate, color, pictures, text, line)
import Brillo.Interface.IO.Game (Event(..), Key(..), KeyState(..), Modifiers(..))

import Types                 (Board, GameState(..), Status(..))
import Logic                 (move, spawnRandom, has2048, movesAvailable, Direction(..))

-- Defining Sizes
boardSize, cellSize :: Int
boardSize = 4
cellSize  = 100
gridPixel :: Float
gridPixel = fromIntegral (boardSize * cellSize)

-- Draw the Current Game State, Draws elements ontop of the baseTile
drawState :: GameState -> Picture
drawState gs =
  let baseTile = rectangleSolid gridPixel gridPixel
  in  color (greyN 0.5) baseTile
    <> drawTiles (board gs)
    <> drawScore (score gs)
    <> drawOverlay (status gs)

--  Draws all Non-Empty Tiles, Acts as a Filter 
drawTiles :: Board -> Picture
drawTiles b = pictures
  [drawTile (i, j) v | (i, row)    <- zip [0..] b, 
                       (j, Just v) <- zip [0..] row]

--  Draws a Single Tile, at a given Postition
drawTile :: (Int, Int) -> Int -> Picture
drawTile (i, j) val =
  translate x y $
    pictures
      [ color tileBg (rectangleSolid s' s'),
        translate (transX) (-25) (scale txtSc txtSc (color black (text (show val))))]
 --Customization to make the Game Look Better
 where
  s    = fromIntegral cellSize
  s'   = s - 10
  half = gridPixel / 2
  x    = -half + fromIntegral j * s + s/2
  y    =  half - fromIntegral i * s - s/2
  tileBg 
   | val ==  2    = yellow 
   | val ==  4    = orange 
   | val ==  8    = rose 
   | val ==  16   = red 
   | val ==  32   = magenta 
   | val ==  64   = violet 
   | val ==  128  = blue 
   | val ==  256  = cyan 
   | val ==  512  = azure 
   | val ==  1024 = aquamarine 
   | otherwise    = white
  transX 
   | val < 10   = -30
   | val < 100  = -40
   | val < 1000 = -50
   | otherwise  = -55
  txtSc 
   | val < 100  = 0.5
   | val < 1000 = 0.4
   | otherwise  = 0.3


--  Draw the Score
drawScore :: Int -> Picture
drawScore sc =
  translate (-half*2) (half + 40) $
    color black (text ("Score: " ++ show sc))
 where
   half = gridPixel / 2

--  Draw Overlay when Game Finishes
drawOverlay :: Status -> Picture
drawOverlay = \case
  Playing -> blank
  Won     -> overlay black (greyN 0.5) "You Win :)"
  Lost    -> overlay black (greyN 0.5) "Game Over :("

--  Overlay Picture Generation
overlay :: Color -> Color -> String -> Picture
overlay fg bg msg =
  let base = rectangleSolid gridPixel gridPixel
  in  color bg base
   <> translate (-300) (-70) (color fg (text msg))

--  Handle Events
handleEvent :: Event -> GameState -> GameState
handleEvent (EventKey key Down _ _) gs =
  case keyToDir key of
    Just dir -> updateGame dir gs
    Nothing  -> gs
handleEvent _ gs = gs

--  Helper Method for Mapping Keys
keyToDir :: Key -> Maybe Direction
keyToDir (Char 'w') = Just GoUp
keyToDir (Char 'W') = Just GoUp
keyToDir (Char 'a') = Just GoLeft
keyToDir (Char 'A') = Just GoLeft
keyToDir (Char 's') = Just GoDown
keyToDir (Char 'S') = Just GoDown
keyToDir (Char 'd') = Just GoRight
keyToDir (Char 'D') = Just GoRight
keyToDir _             = Nothing

--  Update the Game 
updateGame :: Direction -> GameState -> GameState
updateGame dir gs@(GameState b sc gen st) = case st of
  Playing ->
    let (b', pts) = move dir b in
    if b' /= b then
      let (b2, gen') = spawnRandom b' gen
          newScore    = sc + pts
          newStatus   = if has2048 b2 then Won
                        else if not (movesAvailable b2) then Lost
                        else Playing
      in GameState b2 newScore gen' newStatus
    else gs
  _ -> gs
