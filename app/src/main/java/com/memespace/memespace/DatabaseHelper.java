package com.memespace.memespace;

import java.util.List;

/**
 * Abstraction class between the database and app.
 */

public interface DatabaseHelper {
    void getAllMemes(Callback callback);

    interface Callback {
        void onMemesRetrieved(List<Meme> memes);
    }
}
