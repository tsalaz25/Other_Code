{-
Submission rules:

- All text answers must be given in Haskell comment
  underneath the problem header.

- You must submit a single .hs file with the
  following name: firstName-lastName-hw4.hs.
  Failure to do so will result in -10 points.
  
- You will lose 10 points if you put a module statement
  at the top of the file.

- You will lose 10 points for any import statements you have
  in your file and will automatically miss any problems you used
  an imported function on.
  
- If your file doesn't compile you will lose 10 points and miss any
  problems that were causing the compilation errors.

- This means that any function which is causing compiler errors should
  be commented out. There will be no partial credit.
  
- You must use the skeleton file provided and must not alter any type
  signature. If you alter a type signature you will automatically miss
  that problem.

- You will lose 10 points if you include a *main* function in your file.
-}

-- Problem 1 (Exercise 8.1)
{-# OPTIONS_GHC -Wno-missing-methods #-}
import Text.XHtml (rev)
import Distribution.Simple (compilerFlavor, PerCompilerFlavor)
import Language.Haskell.TH (Con)

data Nat = Zero | Succ Nat
    deriving (Eq, Show)

nat2Int :: Nat -> Int
nat2Int Zero     = 0
nat2Int (Succ n) = 1 + nat2Int n

int2Nat :: Int -> Nat
int2Nat 0 = Zero
int2Nat n = Succ (int2Nat (n - 1))

add :: Nat -> Nat -> Nat
add Zero n2      = n2
add (Succ n1) n2 = Succ (add n1 n2)

mult :: Nat -> Nat -> Nat
mult Zero _    = Zero
mult (Succ x) y = add y (mult x y)

-- Tests
-- >>> mult Zero (Succ Zero)
-- Zero
-- >>> mult (Succ (Succ Zero)) (Succ (Succ (Succ Zero)))
-- Succ (Succ (Succ (Succ (Succ (Succ Zero)))))
-- >>> mult (Succ (Succ (Succ Zero))) (Succ (Succ (Succ Zero)))
-- Succ (Succ (Succ (Succ (Succ (Succ (Succ (Succ (Succ Zero))))))))


-- Problem 2 (Exercise 8.3)

data Tree a = Leaf a | Node (Tree a) (Tree a)
    deriving (Eq, Show)

balanced :: Tree a -> Bool
balanced (Leaf _) = True
balanced (Node l r) = 
  abs (leafCount l - leafCount r) <= 1
  && balanced l 
  && balanced r

leafCount :: Tree a -> Int
leafCount (Leaf _)   = 1
leafCount (Node l r) =  leafCount l + leafCount r

-- Tests
-- >>> balanced (Node (Node (Leaf 1) (Leaf 2)) (Node (Leaf 3) (Node (Leaf 4) (Leaf 5))))
-- True
-- >>> balanced (Node (Node (Node (Leaf 1) (Leaf 1)) (Leaf 2)) (Node (Leaf 3) (Node (Leaf 4) (Leaf 5))))
-- True
-- >>> balanced (Node (Node (Node (Node (Leaf 1) (Leaf 1)) (Leaf 1)) (Leaf 2)) (Node (Leaf 3) (Node (Leaf 4) (Leaf 5))))
-- False

-- Problem 3 (Exercise 8.4) 
balance :: [a] -> Tree a
balance [head] = Leaf head
balance xs = Node (balance left) (balance right)
  where
    left = fst (halve xs)
    right = snd (halve xs)

halve :: [a] -> ([a], [a])
halve xs = splitAt (length xs `div` 2) xs
-- Tests
-- >>> balance [1,2,3,4,5]
-- Node (Node (Leaf 1) (Leaf 2)) (Node (Leaf 3) (Node (Leaf 4) (Leaf 5)))

-- Problem 4 (Exercise 8.5)
data Expr = Val Int | Add Expr Expr

folde :: (Int -> a) -> (a -> a -> a) -> Expr -> a
folde f _ (Val n) = f n
folde f g (Add x y) = g (folde f g x) (folde f g y)

-- Tests
-- >>> folde (+1) (*) (Add (Add (Val 1) (Val 2)) (Val 3))
-- 24
-- >>> folde (\i -> [i]) (++) (Add (Add (Val 1) (Val 2)) (Val 3))
-- [1,2,3]

-- Problem 5 (Exercise 8.6)
eval :: Expr -> Int
eval = folde id (+)

size :: Expr -> Int
size = folde (const 1) (+)

-- Tests
-- >>> eval (Add (Add (Val 1) (Val 2)) (Val 3))
-- 6
-- >>> size (Add (Add (Val 1) (Val 2)) (Val 3))
-- 3

-- Problem 6

data ComplexInteger = ComplexInteger Int Int 

real :: ComplexInteger -> Int
real (ComplexInteger x _) = x

imaginary :: ComplexInteger -> Int
imaginary (ComplexInteger _  x) = x

instance Eq ComplexInteger where
  ComplexInteger r i == (ComplexInteger r' i') = r == r' && i == i'

instance Show ComplexInteger where
  show (ComplexInteger r i) = show r ++ "+" ++ show i ++ "i"

instance Num ComplexInteger where
  (ComplexInteger r i) + (ComplexInteger r' i') = 
    ComplexInteger (r + r') (i + i')
  (ComplexInteger r i) * (ComplexInteger r' i') = 
    ComplexInteger (r * r' - i * i' ) (r * r' + i * i')

-- Tests
-- >>> real (ComplexInteger 1 2)
-- 1
-- >>> imaginary (ComplexInteger 1 2)
-- 2
-- >>> (ComplexInteger 1 2) == (ComplexInteger 3 4)
-- False
-- >>> ComplexInteger 1 2
-- 1+2i
-- >>> (ComplexInteger 1 2) * (ComplexInteger 3 4)
-- -5+10i

-- Problem 7

chopN :: Int -> [a] -> [[a]]
chopN n xs =  reverse (fst (foldr go ([],[]) (reverse xs)))
  where 
    go lstTup (y, ys) 
      | length temp == n = ( reverse temp : y , [])
      | otherwise        = (y, temp)
      where temp = lstTup : ys

-- Tests
-- >>> chopN 4 [1..10]
-- [[1,2,3,4],[5,6,7,8]]
-- >>> chopN 8 [1..10]
-- [[1,2,3,4,5,6,7,8]]
-- >>> chopN 1 [1..10]
-- [[1],[2],[3],[4],[5],[6],[7],[8],[9],[10]]
-- >>> chopN 2 [1..10]
-- [[1,2],[3,4],[5,6],[7,8],[9,10]]

-- Problem 8  

subAlphabet :: (Eq a, Enum a) => a -> a -> [a] -> [a]
subAlphabet start end cipher = cipher ++ filter (`notElem` cipher) [start..end]


-- Tests
-- >>> subAlphabet 'A' 'Z' "ZEBRAS"
-- "ZEBRASCDFGHIJKLMNOPQTUVWXY"
-- >>> subAlphabet 1 26 [1,4,6,2,9,10,23,17]
-- [1,4,6,2,9,10,23,17,3,5,7,8,11,12,13,14,15,16,18,19,20,21,22,24,25,26]

-- Problem 9
data Polynomial = Constant Int | MoreTerms Int Polynomial

p = MoreTerms 3 (MoreTerms 4 (Constant 5))
q = MoreTerms 6 (MoreTerms 10 (MoreTerms 35 (Constant 7)))

instance Show Polynomial where
  show poly = showPoly poly 0

--Helper Functions
showPoly :: Polynomial -> Int -> String
showPoly (Constant c) pow       = showTerm c pow
showPoly (MoreTerms c rest) pow = showTerm c pow ++ "+" ++ showPoly rest (pow+1)

showTerm :: Int -> Int -> String
showTerm coef 0 = show coef
showTerm coef 1 = show coef ++ "x"
showTerm coef pow = show coef ++ "x^" ++ show pow

instance Num Polynomial where
  (+) = addPoly
  (*) = mulPoly

--Helper Functions
addPoly :: Polynomial -> Polynomial -> Polynomial
addPoly (Constant x) (Constant y) = Constant (x+y)
addPoly (Constant x) (MoreTerms y rest) = MoreTerms (x+y) rest
addPoly (MoreTerms x rest) (Constant y) = MoreTerms (x+y) rest
addPoly (MoreTerms x xrest) (MoreTerms y yrest) = MoreTerms (x+y) (addPoly xrest yrest)

mulPoly :: Polynomial -> Polynomial -> Polynomial
mulPoly (Constant 0) _ = 0
mulPoly _ (Constant 0) = 0
mulPoly (Constant x) y = scaler x y
mulPoly (MoreTerms x rest) y = addPoly (scaler x y) (shift (mulPoly rest y))

scaler :: Int -> Polynomial -> Polynomial
scaler c (Constant x) = Constant (c * x)
scaler c (MoreTerms x rest) = MoreTerms (c * x) (scaler c rest)

shift :: Polynomial -> Polynomial
shift p = MoreTerms 0 p

evalPoly :: Polynomial -> Int -> Int
evalPoly (Constant c) x = c * (x ^ 0)
evalPoly (MoreTerms c rest) x = c + x * evalPoly rest x

-- Tests
-- >>> p
-- 3 + 4x + 5x^2
-- >>> evalPoly p 2
-- 31

-- Problem 10

data Pair a b = Pair a b

instance (Eq a, Eq b) => Eq (Pair a b) where
  (Pair a b) == (Pair a' b') = a == a' && b == b'

instance (Ord a, Ord b) => Ord (Pair a b) where
  compare (Pair a b) (Pair a' b') = 
    case compare a a' of
      EQ -> compare b b'
      other -> other

-- Tests

-- >>> Pair 1 2 == Pair 1 2
-- True
-- >>> Pair 1 2 == Pair 2 1
-- False
-- >>> Pair 1 2 < Pair 2 1
-- True
-- >>> Pair 1 2 > Pair 1 1
-- True
-- >>> Pair 1 2 > Pair 1 3
-- False


-- Problem 11

safeDivide :: Float -> Float -> Maybe Float
safeDivide x y = if y == 0 then Nothing else Just (x / y)

safeDivide' :: Maybe Float -> Maybe Float -> Maybe Float
safeDivide' (Just x) (Just y) = safeDivide x y
safeDivide' _ _ = Nothing

hm :: [Float] -> Maybe Float
hm [] = Nothing
hm xs = do
  let k = fromIntegral (length xs)
  inverses <- mapM (\x -> safeDivide 1 x) xs
  let total = sum inverses
  safeDivide k total

-- Tests
-- >>> hm [2.0, 2.0]
-- Just 2.0
-- >>> hm [1.0, 1.0]
-- Just 1.0
-- >>> hm [0.5, 0.5, 1.0]
-- Just 0.6
-- >>> hm [1.0, -1.0]
-- Nothing
-- >>> hm [1.0, -2.0, -2.0]
-- Nothing
-- >>> hm [1.0, -2.0]
-- Just 4.0