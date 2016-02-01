import numpy as np
from sklearn import gaussian_process

data = np.genfromtxt('./integ.txt',delimiter=',',dtype='float')
y = data.T[1]
median = np.median(y)
std = np.std(y) 
yy = np.append([y[0]],y)
for i in range(len(yy)):
    if (yy[i] < median - (3*std)) or (yy[i] > median + (3*std)) :
        a = median
        if i != 0:
            a = yy[i - 1]
        b = median
        if (i != len(yy) - 1) and (yy[i+1] > median - (3*std)) and (yy[i+1] < median + (3*std)):
            b = yy[i+1]
        yy[i] = (a+b)/2

z = yy[48:len(yy)]
u = yy[47:len(yy)-1]
meandays = np.zeros(len(z))
stddays = np.zeros(len(z))
week = np.zeros([len(z),7])
for i in range(int(len(z)/48)):
    s = i * 48
    m = np.mean(yy[s:s+48])
    st = np.std(yy[s:s+48])
    for j in range(48):
        meandays[s+j] = m
        stddays[s+j] = st
        week[s+j][i%7] = 1

v = np.c_[u,week,meandays,stddays]
q = z[len(z) - 1]
w = np.zeros(7)
w[int(len(z)/48) % 7] = 1
m = np.mean(yy[len(yy) - 48:len(yy)])
st = np.std(yy[len(yy) - 48:len(yy)])

result = np.zeros(48);

for i in range(48):
  gp = gaussian_process.GaussianProcess(theta0=1e-2, thetaL=1e-4, thetaU=1e-1)
  gp.fit(v[i:len(v):48], z[i:len(v):48]) 
  p = np.array([q, w[0], w[1], w[2], w[3], w[4], w[5], w[6], m, st])
  y_pred, sigma2_pred = gp.predict([p], eval_MSE=True)
  result[i] = y_pred
  q = y_pred

#print(result)
f = open('result.txt', 'w') 
for i in range(len(result)) :
  f.write(str(result[i]))
  f.write('\n')
f.close()