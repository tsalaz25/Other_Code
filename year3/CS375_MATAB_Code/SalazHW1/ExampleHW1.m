% Joel Villarreal
% MATH/CS 375 HW 1, Problem 1
% Due Friday, September 13th

%% (a)

h = 0.1; %step size
lb = -pi; %lower bound
ub = pi; %upper bound

x = lb: h: ub;

cosine = cos(x);
P2 = 1 - 1/2 * x.^2 ;

%% (b)

P4 = 1 - 1/2 * x.^2 + 1/24 * x.^4;

plot(x, cosine);
title("2nd and 4th Taylor Polynomials of cos(x)");

grid on;
hold on;
plot(x, P2);
plot(x, P4);

% FINDINGS: 
% As seen in the graph, the fourth degree Taylor polynomial estimate of
% cos(x) about x = 0, is a better approximation for cos(x) aobut x = 0 than
% the second degree Taylor polynomial estimate.

%% (c)

error2 = abs(cosine - P2);
error4 = abs(cosine - P4);

plot(x, error2);
plot(x, error4);

legend("f(x) = cos(x) ", "P2(x) = 1 - 1/2 * x^2", "P4(x) = 1 - 1/2 " + ...
    "* x^2 + 1/24 * x^4", "error of P2", "error of P4")

hold off;

% Error with h = 0.01
h = 0.01;
x01 = lb: h: ub;
cosine01 = cos(x01);
P201 = 1 - 1/2 * x01.^2 ;
P401 = 1 - 1/2 * x01.^2 + 1/24 * x01.^4;
error201 = abs(cosine01 - P201);
error401 = abs(cosine01 - P401);

% Error with h = 0.001
h = 0.001;
x001 = lb: h: ub;
cosine001 = cos(x001);
P2001 = 1 - 1/2 * x001.^2 ;
P4001 = 1 - 1/2 * x001.^2 + 1/24 * x001.^4;
error2001 = abs(cosine001 - P2001);
error4001 = abs(cosine001 - P4001);

plot(x, error2);
title("Errors with various Steps")

grid on;
hold on;

plot(x, error4);
plot(x01, error201);
plot(x01, error401);
plot(x001, error2001);
plot(x001, error4001);
legend("e2(x), h = 0.1", "e4(x), h = 0.1", "e2(x), h = 0.01", ...
    "e4(x), h = 0.01", "e2(x), h = 0.001", "e4(x), h = 0.001");

hold off;

% Findings: 
% Based on the graph of the different errors, it is clear that what makes
% the most notable reduction in error in this case is not decreasing the
% step size, but increasing the degree of the Taylor Polynomial. When
% zooming in, increased accuracy is found when decreasing step size, but it
% is hard to see, additionally the step size change from 0.01 to 0.001 is
% even harder to distinguish. All that being said, in the case of
% estimating points with Taylor Expansion, it seems that increasing the 
% degree of that Taylor polynomial is a more useful tool than increasing
% the number of steps, although both likely have their unique uses. 