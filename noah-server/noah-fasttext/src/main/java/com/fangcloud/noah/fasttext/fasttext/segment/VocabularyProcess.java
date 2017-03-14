package com.fangcloud.noah.fasttext.fasttext.segment;

import java.util.List;

public interface VocabularyProcess {

    public List<InternalElement> postProcess(List<char[]> lines);

}
