package jp.co.saison.training.bookmanagement.application.usecases;

public interface Usecase<I, O> {
    O handle(I inputData);
}
