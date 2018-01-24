result <- dbSendQuery(connection, "SELECT * FROM public.movie_mpaa");

mpaa <- dbFetch(result, n = -1);

mpaaCorpus <- Corpus(VectorSource(mpaa$mpaa_reason));

mpaaCorpus <- tm_map(mpaaCorpus, tolower);
mpaaCorpus <- tm_map(mpaaCorpus, removePunctuation);
mpaaCorpus <- tm_map(mpaaCorpus, removeWords, stopwords("en"));
mpaaCorpus <- tm_map(mpaaCorpus, stemDocument);

mpaaFrequencies <- DocumentTermMatrix(mpaaCorpus);

mpaa <- data.frame(rating = mpaa$mpaa_rating, as.matrix(removeSparseTerms(mpaaFrequencies, 0.995)));

mpaaSplit <- sample.split(mpaa$rating, SplitRatio = 0.8);
mpaaTrain <- subset(mpaa, mpaaSplit == TRUE);
mpaaTest <- subset(mpaa, mpaaSplit == FALSE);

mpaaCART <- rpart(rating ~ reason, data = mpaaTrain, control = rpart.control(cp = 0.01), method = "class");
png("{{param}}");
prp(mpaaCART, main = "CART Model");
dev.off();

predictMpaa <- predict(mpaaCART, type = "class", newdata = mpaaTest);
mpaaConfMatrix <- table(mpaaTest$rating, predictMpaa);
mpaaConfMatrix{{retrieve}};

mpaaAcc <- (mpaaConfMatrix[1, 1] + mpaaConfMatrix[2, 2] + mpaaConfMatrix[3, 3] + mpaaConfMatrix[4, 4] + mpaaConfMatrix[5, 5] + mpaaConfMatrix[6, 6]) / sum(colSums(mpaaConfMatrix));
mpaaAcc{{retrieve}};

mpaaInput <- mpaa[mpaa$id == {{param}}, ];
predictInput <- predict(mpaaCART, type = "class", newdata = mpaaInput);
data.frame(predictInput){{retrieve}};