package io;

import model.Word;

import java.util.ArrayList;
import java.util.List;

public class Row<E extends Word> {

    private int position;
    private List<E> listObject;
    private int HASH_CONST = 17;
    private boolean deleteTag;
    private String sentences;

    public Row() {
        super();
    }

    public Row(List<E> listObject) {
        super();
        this.listObject = listObject;
        deleteTag = false;
    }

    public Row(int position, List<E> listObject) {
        super();
        this.position = position;
        this.listObject = listObject;
        deleteTag = false;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<E> getListObject() {
        return listObject;
    }

    public void setListObject(List<E> listObject) {
        this.listObject = listObject;
    }

    public boolean isDeleteTag() {
        return deleteTag;
    }

    public void setDeleteTag(boolean deleteTag) {
        this.deleteTag = deleteTag;
    }

    public String getSentences() {
        return sentences;
    }

    public void setSentences(String sentences) {
        this.sentences = sentences;
    }

    @Override
    public int hashCode() {

        int hash_code = HASH_CONST;

        int childHash = listObject == null ? 1 : 0;
        int sentencesHash = sentences == null ? 1 : 0;
        hash_code = hash_code + 31 * childHash;
        hash_code = hash_code + 31 * position;
        hash_code = hash_code + 31 * sentencesHash;

        return hash_code;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (!(o instanceof Row)) {
            return false;
        }

        Row i = (Row) o;
        if (i.getListObject().size() != listObject.size()) {
            return false;
        }

        for (Object s : i.getListObject()) {
            if (!listObject.contains(s)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Row clone() throws CloneNotSupportedException {
        List<Word> list = new ArrayList<>();
        for (Word s : this.getListObject()) {
            Word w = new Word();
            w.setWord(s.getWord());
            w.setType(s.getType());
            w.setOriginalWord(s.getOriginalWord());
            w.setSentences(s.getSentences());
            list.add(w);
        }

        Row row = null;
        try {
            row = (Row) super.clone();
            row.setListObject(list);
            row.position = getPosition();
            row.setDeleteTag(isDeleteTag());

            return row;
        } catch (CloneNotSupportedException e) {
            new AssertionError();
        }
        return row;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        if (null != getListObject() && getListObject().size() > 0) {
            for (Word w : listObject) {
                builder.append(w.getWord() + " ");
            }
        }
        return builder.toString();
    }
}