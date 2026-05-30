%% Function for Creating a Narual Cubic Spline (NCS) || Author: Tomas Salaz

function S_vals = cubic_spline(x, y, x_eval)
    % This function constructs and evaluates a natural cubic spline.
    % Inputs:
    %   x - known x values (nodes)
    %   y - function values at nodes (e.g., tanh(x))
    %   x_eval - points where the spline should be evaluated
    % Output:
    %   S_vals - spline values at x_eval
    
    n = length(x) - 1;  % number of intervals

    % Step 1: Compute the intervals and y differences
    h = diff(x);        % interval widths
    b = diff(y) ./ h;   % slopes of secant lines between points
    
    % Step 2: Set up the tridiagonal system for second derivatives
    % Create the vector d and matrix A
    A = zeros(n+1, n+1);
    d = zeros(n+1, 1);
    
    % Natural spline boundary conditions
    A(1,1) = 1;
    A(end,end) = 1;
    
    % Interior points
    for i = 2:n
        A(i, i-1) = h(i-1);
        A(i, i) = 2 * (h(i-1) + h(i));
        A(i, i+1) = h(i);
        d(i) = 3 * (b(i) - b(i-1));
    end
    
    % Solve for second derivatives
    M = A \ d;  % second derivatives
    
    % Step 3: Evaluate the spline at each x_eval
    S_vals = zeros(size(x_eval));
    for k = 1:length(x_eval)
        % Find the interval j such that x(j) <= x_eval(k) < x(j+1)
        j = find(x_eval(k) >= x, 1, 'last');
        if j == n+1, j = n; end  % for cases where x_eval(k) == x(end)
        
        % Calculate the spline polynomial on this interval
        h_j = x(j+1) - x(j);
        A_j = (M(j+1) - M(j)) / (6 * h_j);
        B_j = M(j) / 2;
        C_j = b(j) - h_j * (2 * M(j) + M(j+1)) / 6;
        D_j = y(j);
        
        % Compute the difference from x(j)
        dx = x_eval(k) - x(j);
        
        % Evaluate the spline polynomial
        S_vals(k) = A_j * dx^3 + B_j * dx^2 + C_j * dx + D_j;
    end
end
