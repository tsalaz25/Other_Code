import Text.XHtml (width)
{-
Submission rules:

- All text answers must be given in Haskell comment
  underneath the problem header.

- You must submit a single .hs file with the
  following name: firstName-lastName-hw3.hs.
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

-- Problem 1
--          funtion      arg1 arg2  out
curry' :: ((a, b) -> c) -> a -> b -> c
curry' f a b =  f (a, b)

--            function    args(1, 2)  out 
uncurry' :: (a -> b -> c) -> (a, b) -> c
uncurry' f (a , b)= f a b


-- Problem 2
{-UNFOLD REFERENCE
P=>Predicate  H=>Head producing Funct T=>Tail producing Funct-}

unfold :: (b -> Bool) -> (b -> a) -> (b -> b) -> b -> [a]
unfold p h t x
  | p x = []
  | otherwise = h x : unfold p h t (t x)


chop8 :: [a] -> [[a]]
chop8 xs = unfold (null) (take 8) (drop 8) xs

map' :: (a -> b) -> [a] -> [b]
map' f xs = unfold (null) (f.head) (drop 1) xs

iterate' :: (a -> a) -> a -> [a]
iterate' f zeroID = unfold (const False) (id) (f) zeroID


-- Problem 3
concatER :: [[a]] -> [a]
concatER [] = []
concatER (x:xs) = x ++ concatER xs 

concatFR :: [[a]] -> [a]
concatFR xs = foldr (++) [] xs 

concatFL :: [[a]] -> [a]
concatFL xs = foldl (++) [] xs


-- Problem 4
disjunction2 :: (a -> Bool) -> (a -> Bool) -> a -> Bool
disjunction2 p1 p2 x = p1 x || p2 x 


-- Problem 5
disjunction :: [a -> Bool] -> a -> Bool
disjunction ps x = or (foldr (\p ps -> p x : ps) [] ps)
{- From Notes
map'' f xs = combine (\x recur -> f x : recur) [] xs-}

-- Problem 6
deleteDupes :: Eq a => [a] -> [a]
deleteDupes xs = foldr go [] xs
  where
    go x xs = if x `elem` xs then x:(filter (/=x) xs) else x:xs

-- Problem 7
tally :: (a -> Bool) -> [a] -> Int
tally p xs = foldl go 0 xs
  where 
    go acc y = if p y then acc+1 else acc


-- Problem 8
bangBang :: [a] -> Int -> a
bangBang xs n = foldr go (last xs) (zip [0..] xs)
  where 
    go (x,y) rest = if x == n then y else rest 


-- Problem 9
increasing :: Ord a => [a] -> Bool
increasing xs = foldr go True (zip xs (tail xs))
 where 
  go (x,y) recur = x <= y && recur 


-- Problem 10
decimate :: [a] -> [a]
decimate xs = foldl go [] (zip [1..] xs)
  where 
    go recur (i, x) = if i `mod` 10 == 0 then recur else recur ++ [x]


-- Problem 11
lookup' :: Eq a => a -> [(a,b)] -> b        
lookup' x ((y,z):ys) |    x == y = z
                     | otherwise = lookup' x ys

encipher :: Eq a => [a] -> [b] -> [a] -> [b]
encipher xs ys zs = map go zs
  where 
    cipher = zip xs ys 
    go zs   = lookup' zs cipher 


-- Problem 12
prefixSum :: Num a => [a] -> [a]
prefixSum xs = foldl go [] xs
  where 
    go [] x = [x]
    go acc x = acc ++ [x + last acc]


-- Problem 13
minesweeper :: [String] -> [String]
minesweeper grid = [ [convertBoard r c | c <- [0..width-1]] | r <- [0..height-1] ]
  where 
    height = length grid
    width  = length (head grid)

    convertBoard :: Int -> Int -> Char
    convertBoard row col | curr   == '*' = '*'
                         | mCount == 0   = '.'
                         | otherwise     = intToDigit mCount
      where
        curr = (grid !! row ) !! col
        mCount = getMineCount row col grid
  
--Helper for getting # of Adj Mine
getMineCount :: Int -> Int -> [String] -> Int
getMineCount row col grid = length [() | (adjRow, adjCol) <- directions, let r = row+adjRow, let c = col+adjCol,
                                 inBounds r c, (grid !! r)!!c == '*']
  where 
    height = length grid
    width  = length (head grid)
    directions = [(-1,-1), (-1,0), (-1,1),
                  ( 0,-1),         ( 0,1),
                  ( 1,-1), ( 1,0), ( 1,1)]
    inBounds r c = r>=0 && r<height && c>=0&&c < width

-- Helper function provided
intToDigit :: Int -> Char
intToDigit 0 = '.'
intToDigit 1 = '1'
intToDigit 2 = '2'
intToDigit 3 = '3'
intToDigit 4 = '4'
intToDigit 5 = '5'
intToDigit 6 = '6'
intToDigit 7 = '7'
intToDigit 8 = '8'
intToDigit 9 = '9'