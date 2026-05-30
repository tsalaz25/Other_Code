-- Homework 1 Due February 5th 11:59 pm

{-
Submission rules:

- All text answers must be given as Haskell comments.

- You must submit a single .hs file with the
  following name: firstName-lastName-hw5.hs.
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

-- Problem 1 Prelude Types (1 pt each, 15 pts)
-- Give the type of each of the following builtin Haskell functions.
-- Provide your answers in Haskell comments beside each function (after the ::).

-- head :: [a] -> a
-- tail :: [a] -> [a]
-- fst :: (a,b) -> a
-- snd :: (a,b) -> b
-- length :: Foldable t => t a -> Int
-- null :: Foldable t => t a -> Bool
-- take :: Int -> [a] -> [a]
-- drop :: Int -> [a] -> [a]
-- sum :: (t, a) => t a -> a
-- product :: (Foldable t, Num a) => t a -> a
-- (++) :: [a] -> [a] -> [a]
-- (!!) :: [a] -> Int -> a
-- elem :: (Foldable t, Eq a) => a -> t a -> Bool
-- (:) :: a -> [a] -> [a]
-- last :: [a] -> a

-- Problem 2 More Types (1 pt each, 15 pts)
-- What is the type of the following Haskell expressions?
-- Provide your answers in Haskell comments beside each expression (after then ::).

-- True :: Bool
-- not True :: Bool
-- not :: Bool -> Bool
-- 1 + 2 :: Num a => a
-- (+) 1 2 :: Num a => a
-- (+) 1 :: Num a => a -> a
-- (+) :: Num a => a -> a -> a
-- [True, False] :: [Bool]
-- [1, 2, 3] :: Num a => [a]
-- [1, 2, 3] ++ [4, 5, 6] :: Num a => [a]
-- [] :: [a] 
-- [[]] :: [[a]]
-- [[], []] :: [[a]]
-- take 10 [] :: [a]
-- take 10 :: [a] -> a

-- Problem 3 Layout (3 pts each, 15 pts)
-- Fix the error in each of the following pieces of code.

--Indentation
f = x + y
  where
    x = 1
    y = 2

--arg in haskell cannot be UpperCase, 
--g X Y = X + Y
g x y = x + y

--Same thing as above, Function name need to be lowecase
--Foo x y = x + y
foo x y = x + y

--List Syntax
h = [1,2,3] ++ [4,5,6]

--Indentation
u = x * y
  where
    x = 1
    y = 2

-- Problem 4 Classes (3 pts each, 18 pts)
-- For each problem give a list of functions separated by commas.
-- For example, the Eq typeclass defines the (==), (/=) functions.
-- Provide your answers in Haskell comments below each question.

-- Which functions does the Ord typeclass define?
--    (<), (<=), (>), (>=), max, min 
-- Which functions does the Show typeclass define?
--    s howsPrec, show, showList
-- Which functions does the Read typeclass define?
--    readsPrec and readList
-- Which functions does the Num typeclass define?
--    (+), (-), (*), negate, abs, signum, fromInteger
-- Which functions does the Integral typeclass define?
--    quot, rem, div, mod, quotRem, divMod, toInteger
-- Which functions does the Fractional typeclass define?
--    (/), recip, fromRational

-- Problem 5 Functions (37 pts)
-- (5 pts) (Chapter 4 Exercise 1)
halve :: [a] -> ([a], [a])
halve xs = (take n xs, drop n xs)
  where n = length xs `div` 2


-- (10 pts) (Chapter 4 Exercise 2)
fourth1 :: [a] -> a
fourth1 xs = head (tail (tail (tail xs)))

fourth2 :: [a] -> a
fourth2 xs = xs !! 3

fourth3 :: [a] -> a
fourth3 (_:_:_:x:_) = x

-- (22 pts) (Chapter 4 Exercise 8)
luhnDouble :: Int -> Int
luhnDouble x | x < 5 = x*2
             | otherwise = 1 + (x*2)`mod`10

luhn :: Int -> Int -> Int -> Int -> Bool
luhn x y z j = (luhnDouble(x) + y + luhnDouble(z) + j) `mod` 10 == 0