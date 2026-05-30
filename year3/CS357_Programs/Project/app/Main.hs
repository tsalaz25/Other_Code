import System.Random   (getStdGen)
import Brillo  
import Types            
import Logic            (initState)
import Graphics         (drawState, handleEvent)

main :: IO ()
main = do
  g0 <- getStdGen
  let gs0 = initState g0
  play (InWindow "2048" (800,800) (100,100))
       white 60 gs0 drawState handleEvent (\_ gs -> gs)
