module Main where
import Brillo
import System.Random

main :: IO ()
main = display
  (InWindow "Nice Window" (200, 200) (10, 10))
  white
  (Circle 80)

let Rolls :: RandomGrn g => Int -> g -> [Word]
  rolls n = fst. uniformListR n (1,6)
  pureGen = mkStdGen 137
in 
  rolls 10 pureGen :: [Word]


{-
data List a = Nil | Cons a (List a)

data State = 
  MkState {
    coords :: [(Float, Float)],
    colors :: [Color]
  } 
  deriving (Eq, Show)

initState :: State
initState = 
  MkState [(100,100) , (200,200), (300,300)] [white, red, blue]

stateToPicture :: State -> State
stateToPicture s = Pictures () zipWith mkCircle (coords s) (colors s)
  where 
    mkCircle (x,y) color = Color color (Translate x y (Circle 100))
-- Pictures [ mkCircle crd, clr | (crd, clr) <- zip (coords s) (colors s)  ]

--stateToState :: State -> States
--stateToState s = 
--  s {coords = map (\(x+y) <- (x-1,y-1))}
--  where
--    crd = coord s 



main = 
  simulate (InWindow "Nice Window " (500,500),(10,10))
  black 
  60
  initState

module Main where
import Brillo 
import System.Random

ws = 1024
window = InWindow "Tree Viz" (ws,ws) (10,10)
bg = white

main :: IO ()
main = display window bg -}