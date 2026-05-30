%Tomas Salaz Math/CS375 Numerical Methods
%HW5 due 11/1

f = @(x) 1./(1 + x.^2);             %Define Runge Function
xFine = linspace(-5,5,1000);        %Fine-Mesh Plotting

%% (A) Plot 1st-Degree Spline using various Knots
knots = [21,41,81];
figure;
hold on;
colors = ['r','y','b'];
markerSizes = [4,3,2];

for k = 1:length(knots)
    n = knots(k);
    x_knots = linspace (-5,5,n);        %Define the Knots on the interval
    y_vals = f(x_knots);                %Evaluate the funtinon at the Knots
    Spline = Spline1(x_knots, y_vals, xFine);              %Evaluate Spline
    plot(xFine, Spline, 'Color', colors(k));
    plot(x_knots, y_vals, 'o', 'Color', colors(k), 'MarkerSize', markerSizes(k), 'MarkerFaceColor' , colors(k));
end
legend ('S21(x)', '','S41(x)','','S81(x)','');
hold off;

%% (B) Using 21 ChebyShev Nodes to Construct a Spline
n_ChebyShev = 21;
k = 0:(n_ChebyShev-1);
x_ChebyShev = 5*cos((2*k+1)*pi / (2*n_ChebyShev));       %ChebyShev Nodes
x_Normal = linspace (-5,5,21);                           %Normal Nodes
y_ChebyShev = f(x_ChebyShev);                            %Evaluate at Nodes
y_Normal = f(x_Normal);
S = Spline1(x_Normal,y_Normal,xFine);
G = interp1(x_ChebyShev,y_ChebyShev,xFine,'linear');     %Linear Interpolation 
                                                         %funtion in MatLab
figure;
hold on;

plot(x_ChebyShev, y_ChebyShev, 'ro', 'MarkerSize', 3, 'MarkerFaceColor','r');
plot(x_Normal, y_Normal, 'bo', 'MarkerSize', 3, 'MarkerFaceColor','b');
plot (xFine, G, 'r-', 'DisplayName', 'G20(x)');
plot (xFine, S, 'b-', 'DisplayName','S21(x)');
legend('','','G20(x)','S21(x)');
hold off;