%Tomas Salaz Math/CS375 Numerical Methods
%HW3 due 10/5

%% (b) Use Bisection to find + root of 2x(1+x^2)^-1=arctan(x)
%Using the initial interval [1,2] for 
%f(x)=2x(1+x^2)^-1-arctan(x);   
%f(1)=1-arctan(1)= 0.214602
%f(x)=4/5-arctan(2)= -0.307149

f = @(x) (2*x)/(1+x.^2)-atan(x);  %Function; Find Root
a = 1; b = 2;                         %Upper and Lower Bounds
tol = 1e-8;                           %Tolerance
max_i = 25;                           %Max Iterations
arr = zeros(1,max_i);
%Error Check
if f(a)*f(b) > 0
    fprintf("Error, Signs arent Opposite");
end
%Bisection Method
for i = 1: max_i
    m = (a+b)/2;    %Midpoint
    arr(i) = m;
    fm = f(m);      %Val at Midpoint

   %Check Tolerance
   if abs(fm) < tol
       fprintf("Converged to x = %.8f in %d iterations\n",m,i);
       break;
   end 
   %Update Interval
   if f(a)*fm < 0
       b = m;
   else 
       a = m;
   end 
end

figure;
fprintf('%.8f\n',arr);
xbound = linspace(-1,2,1750);
xline(0, '-black', LineWidth=.5);
yline(0, '-black',LineWidth=.5); hold on;
vals = arrayfun(f,xbound);
plot(xbound,vals);
bis = arrayfun(f,arr);
plot(arr,bis,'bo', 'MarkerSize', 3 , 'MarkerFaceColor', 'b');
xlim(-1,2.5);
axis equal;