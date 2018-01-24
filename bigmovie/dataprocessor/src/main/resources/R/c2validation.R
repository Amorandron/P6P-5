predictMpaa <- predict(mpaaCART, type = "class", newdata = mpaaTest);
mpaaConfMatrix <- table(mpaaTest$rating, predictMpaa);
mpaaConfMatrix{{retrieve}};

mpaaAcc <- (mpaaConfMatrix[1, 1] + mpaaConfMatrix[2, 2] + mpaaConfMatrix[3, 3] + mpaaConfMatrix[4, 4] + mpaaConfMatrix[5, 5] + mpaaConfMatrix[6, 6]) / sum(colSums(mpaaConfMatrix));
mpaaAcc{{retrieve}};