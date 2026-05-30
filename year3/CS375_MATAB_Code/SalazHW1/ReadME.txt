NewtonsMethodHWA.m Script
This is a MATLAB script for showing 2 plots
-Figure 1 plots the following functions on the interval [-pi,pi]
 f(x) = cos(x)
 p2(x) = 1-x^2/2          *The 2nd Taylor Polynomial*
 p4(x) = x-x^2/2+x^4/24   *The 4th Taylor Polynomial*

-Figure 2 plots the following error functions on the interval [-pi,pi]
 r2(x) = x^2*sin(x)/6
 r4(x) = x^5*-sin(x)/120
-Figure 2 functions [r2(x), r4(x)] are tested with various values, those being 
 x1 = .1
 x2 = .01
 x3 = .001

SUMMARY
Figure 1: Plot shows that the p4x function is a better approximation for 
the cos(x). This is due to the degree being higher and the function having
more turning points to better match the cos(x) function. Both p2x and p4x 
are good approximations for values near 0 but as the functions reach the 
endpoints p4x become a better approximation which implies the higher degree 
polynomials will have more accuracy.

Figure 2: Plot shows the errors of the different Taylor Remainder Theorems,
r2x and r4x. The x1,x2,and x3 values are the different test, so the higher 
the amplitude of the curve the worse the approximation (NOTE: we must look 
at the amplitude since Taylor Theorem is defined in absolute value). The 
plot shows that the approximations get better as the number gets close to 0
regardless of the degree of polynomial.

[LEAST ERROR] r4(.001) < r2(.001) < r4(.01) < r2(.01) < r4(.1) < r2(.1) [MOST ERROR]


IVP.m and IVPEuler.m Scripts
-Both Files are MATLAB Scripts, each producing 1 plot for the same funcion,
on the same interval [0,200], and the same Rate of Growth (r). The Initial
Capacity is changed and tested with 100,750,and 1300. 
-Implementation: 
Both files are similar since the End goal is the same, the 
IVP model uses anonamous function definitions and the Eulers model uses loops
and do pust valuse into a vectos, which is then plotted.
-Comparison:
Both Schemes work and the Funciton Approachs the Carring Capacity at a similar
rate according to the plot. If you Zoom in on the plot, near the end of the
plotted interval and compare, My IVT.m scheme has MORE Error so the better 
scheme is the Euler Implementation. The difference between the Schemes is
<10^-2
-Other:
IVPEuler.m has some different values foe 'step' variable, so the smaller the
step the more accurate the model. I'm not sure how the 'ode45' funciton
works but I assume that the funciton evaluations are not as percise as using
smaller 'steps', which can be adjusted.
More accuracy can be achieved by running the loops longer and breaking them 
once a certain degree of accuray is reched.