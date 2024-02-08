/**
 * Represnts a list of musical tracks. The list has a maximum capacity (int),
 * and an actual size (number of tracks in the list, an int).
 */
class PlayList {
    private Track[] tracks;  // Array of tracks (Track objects)   
    private int maxSize;     // Maximum number of tracks in the array
    private int size;        // Actual number of tracks in the array

    /**
     * Constructs an empty play list with a maximum number of tracks.
     */
    public PlayList(int maxSize) {
        this.maxSize = maxSize;
        tracks = new Track[maxSize];
        size = 0;
    }

    /**
     * Returns the maximum size of this play list.
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Returns the current number of tracks in this play list.
     */
    public int getSize() {
        return size;
    }

    public Track[] getTracks() {
        return tracks;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Method to get a track by index
     */
    public Track getTrack(int index) {
        if (index >= 0 && index < size) {
            return tracks[index];
        } else {
            return null;
        }
    }

    /**
     * Appends the given track to the end of this list.
     * If the list is full, does nothing and returns false.
     * Otherwise, appends the track and returns true.
     */
    public boolean add(Track track) {
        if (this.size == this.tracks.length) {
            return false;
        }
        this.tracks[this.size] = track;
        this.size++;
        return true;
    }


    public void setTracks(Track[] tracks) {
        this.tracks = tracks;
    }

    /**
     * Returns the data of this list, as a string. Each track appears in a separate line.
     */
    //// For an efficient implementation, use StringBuilder.
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int i = 0; i < size; i++) {
            sb.append(tracks[i].toString());
            sb.append("\n");
        }
        return sb.toString();
    }


    /**
     * Removes the last track from this list. If the list is empty, does nothing.
     */
    public void removeLast() {
        if (getSize() > 0) {
            tracks[size - 1] = null;
            size--;
        }
    }

    /**
     * Returns the total duration (in seconds) of all the tracks in this list.
     */
    public int totalDuration() {
        int totalDuration = 0;
        for (int i = 0; i < this.size; i++) {
            totalDuration += this.tracks[i].getDuration();


        }
        //// replace the following statement with your code
        return totalDuration;
    }

    /**
     * Returns the index of the track with the given title in this list.
     * If such a track is not found, returns -1.
     */
    public int indexOf(String title) {
        for (int i = 0; i < this.tracks.length; i++) {
            if (this.tracks[i] != null) {
                if (this.tracks[i].getTitle().equals(title)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Inserts the given track in index i of this list. For example, if the list is
     * (t5, t3, t1), then just after add(1,t4) the list becomes (t5, t4, t3, t1).
     * If the list is the empty list (), then just after add(0,t3) it becomes (t3).
     * If i is negative or greater than the size of this list, or if the list
     * is full, does nothing and returns false. Otherwise, inserts the track and
     * returns true.
     */
    public boolean add(int i, Track track) {
        if (i >= 0 && i <= size && add(track)) {
            // Shift elements to make room for the new track at index i
            for (int j = size - 1; j > i; j--) {
                this.tracks[j] = this.tracks[j - 1];
            }

            // Insert the new track at index i
            this.tracks[i] = track;
            return true;
        }
        return false;
    }

    /**
     * Removes the track in the given index from this list.
     * If the list is empty, or the given index is negative or too big for this list,
     * does nothing and returns -1.
     */
    public void remove(int i) {
        if (this.size != 0 && i > 0 && i < this.size) {
            if (i == this.size - 1) {
                this.tracks[i] = null;
            }
            else {
                for (int j = i; j < this.size - 1; j++) {
                    this.tracks[j] = this.tracks[j+1];
                }
            }
            this.size--;
        }
    }

    /**
     * Removes the first track that has the given title from this list.
     * If such a track is not found, or the list is empty, or the given index
     * is negative or too big for this list, does nothing.
     */
    public void remove(String title) {
        int idx = indexOf(title);
        if (idx != -1 && this.size != 0 && idx < this.size) {
            remove(idx);
        }
    }

    /**
     * Removes the first track from this list. If the list is empty, does nothing.
     */
    public void removeFirst() {
        if (size > 0) {
            remove(0);
        }
    }

    /**
     * Adds all the tracks in the other list to the end of this list.
     * If the total size of both lists is too large, does nothing.
     */
    //// An elegant and terribly inefficient implementation.
    public void add(PlayList other) {
        int totalSize = size + other.getSize();

        if (totalSize <= maxSize) {

            int count = 0;
            for (int i = size; i < totalSize; i++) {
                this.tracks[i] = other.getTracks()[count];
                count++;
            }
            setSize(totalSize);
        }
    }


    /**
     * Returns the index in this list of the track that has the shortest duration,
     * starting the search in location start. For example, if the durations are
     * 7, 1, 6, 7, 5, 8, 7, then min(2) returns 4, since this the index of the
     * minimum value (5) when starting the search from index 2.
     * If start is negative or greater than size - 1, returns -1.
     */
    private int minIndex(int start) {
        int shortDur = 1000000000;
        int idx = 0;
        if (start < 0 || start > size - 1) {
            return -1;
        }
        for (int i = start; i < size; i++) {
            if (tracks[i].getDuration() < shortDur) {
                shortDur = tracks[i].getDuration();
                idx = i;
            }

        }
        return idx;
    }

    /**
     * Returns the title of the shortest track in this list.
     * If the list is empty, returns null.
     */
    public String titleOfShortestTrack() {
        if (size == 0) {
            return null;
        }
        return tracks[minIndex(0)].getTitle();
    }

    /**
     * Sorts this list by increasing duration order: Tracks with shorter
     * durations will appear first. The sort is done in-place. In other words,
     * rather than returning a new, sorted playlist, the method sorts
     * the list on which it was called (this list).
     */
    public void sortedInPlace() {
        for (int i = 0; i < size; i++) {
            int shortIdx = minIndex(i);
            Track temp = tracks[i];
            tracks[i] = tracks[shortIdx];
            tracks[shortIdx] = temp;
            // Uses the selection sort algorithm,
            // calling the minIndex method in each iteration.
            //// replace this statement with your code
        }
    }
}