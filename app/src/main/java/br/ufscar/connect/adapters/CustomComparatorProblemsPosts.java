package br.ufscar.connect.adapters;

import java.util.Comparator;

import br.ufscar.connect.models.FeedProblemPost;

/**
 * Created by Arthur on 07/02/2017.
 */
public class CustomComparatorProblemsPosts implements Comparator<FeedProblemPost> {// may be it would be Model
    @Override
    public int compare(FeedProblemPost obj1, FeedProblemPost obj2) {
        return obj2.getPublDate().compareTo(obj1.getPublDate());// compare two objects
    }
}
