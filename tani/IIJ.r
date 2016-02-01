IIJ <- function(train.file.name) {
	print(paste("start at:", date()))
	# �����x�N�g���̎�����
	dim.feat <- 7 * 48
	
	# �n�C�p�[�p�����[�^�ݒ�
	#   sigma:�m�C�Y���x
	#   gamma:kernel�w�����W��
	#   a:kernel���`:�w����
	#   b:K�s��萔
	params <- c(1 * 10 ^ -1, 1 * 10 ^ -6, 0.01, 1.0)
	names(params) <- c("sigma", "gamma", "a", "b")

	train.x <- file.read(train.file.name, dim.feat, 0)
	train.y <- file.read(train.file.name, dim.feat, 1)
	test.x <- file.read(train.file.name, dim.feat, 2)
	
#	print(dim(train.x))
#	print(length(train.y))
#	return(dim(test.x))
	
	# �s��K�����߂�i��A�j
	K <- learn(train.x, train.y, params)
		
	# K��p���Đ�����s��
	est <- estimate(test.x, train.x, params, K)
	print(paste("end at:", date()))
#	print(params)
	return(est)
}

file.read <- function(file.name, dim.feat, xy) {
	# �f�[�^���`
	read.data <- read.table(file.name, sep = ",")
	data <- matrix(0, nrow=nrow(read.data) - dim.feat + 1, ncol=dim.feat + 1)
	for (i in 1:nrow(data)) {
		data[i,] <- t(read.data[i:(i+dim.feat),2])
	}
	
	if (xy == 0) {
		return(data[1:(nrow(data) - 48), 1:(ncol(data) - 1)])
	} else if (xy == 1) {
		return(data[49:nrow(data), 1])
	} else if (xy == 2) {
		return(data[(nrow(data) - 47):nrow(data), 1:(ncol(data)-1)])
	}
}


learn <- function(train.x, train.y, params) {
	#kernel��p���čs��K�����߂�
	K <- kernel.linear.exp(train.x, train.x, params)
	K <- K + params["sigma"] * diag(nrow(K))
	solve(K) %*% train.y
}

estimate <- function(testVector, train.x, params, K) {
	#�s��K��p���Đ�����s��
	k <- kernel.linear.exp(train.x, testVector, params)
	t(k) %*% K
}

kernel.linear.exp <- function(x1, x2, params) {
	# 2�����pkernel�֐�
	# ��2��(�w����)
	euc <- euc(as.matrix(x1), as.matrix(x2))
	# ��1��(���`��)�Ƒ�2���̏d�ݕt���a���o��
	params["b"] * (params["a"] * x1 %*% t(x2) + (1 - params["a"]) * exp ( - params["gamma"] * euc))
}

euc <- function(x1, x2) {
	sum <- matrix(0, nrow = length(x1[,1]), ncol = length(x2[,1]))
	for (i in 1:length(x1[1,])) {
		sub <- outer(x1[,i], x2[,i], "-")
		sum <- sum + sub ^ 2
	}
	return(sum)
}

error <- function(a) {
	tmp <- a[,1] - a[,2]
#	tmp <- a[,1]
	tmp <- tmp * tmp
	sum(tmp)
}
