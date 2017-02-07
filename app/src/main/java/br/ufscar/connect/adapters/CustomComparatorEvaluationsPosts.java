package br.ufscar.connect.adapters;

import java.util.Comparator;

import br.ufscar.connect.models.FeedEvaluationPost;
import br.ufscar.connect.models.FeedProblemPost;

/**
 * Created by Arthur on 07/02/2017.
 */
public class CustomComparatorEvaluationsPosts implements Comparator<FeedEvaluationPost> {// may be it would be Model
    @Override
    public int compare(FeedEvaluationPost obj1, FeedEvaluationPost obj2) {
        return obj2.getDate().compareTo(obj1.getDate());// compare two objects
    }
}
