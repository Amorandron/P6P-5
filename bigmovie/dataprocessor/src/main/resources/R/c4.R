# Author: Jan

file <- "{{param}}";

result <- dbSendQuery(connection, "SELECT * FROM public.movie_actor_age");

age <- dbFetch(result, n = -1);
age <- na.omit(age);

ggplot(subset(age, gender %in% c("F", "M")), aes(x=age, y=percentage, color=gender))
    + geom_smooth(stat="identity");
ggsave(file);