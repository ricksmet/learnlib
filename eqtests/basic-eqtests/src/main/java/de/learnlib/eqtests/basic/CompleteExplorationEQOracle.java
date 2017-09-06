/* Copyright (C) 2013-2017 TU Dortmund
 * This file is part of LearnLib, http://www.learnlib.de/.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.learnlib.eqtests.basic;

import java.util.Collection;
import java.util.stream.Stream;

import com.google.common.collect.Streams;
import de.learnlib.api.MembershipOracle;
import net.automatalib.automata.concepts.DetOutputAutomaton;
import net.automatalib.commons.util.collections.CollectionsUtil;
import net.automatalib.words.Word;

/**
 * Implements an equivalence check by complete exploration up to a given depth, i.e., by testing all possible sequences
 * of a certain length within a specified range.
 *
 * @param <I>
 *         input symbol type
 * @param <D>
 *         output domain type
 *
 * @author Malte Isberner
 */
public class CompleteExplorationEQOracle<I, D> extends AbstractTestWordEQOracle<DetOutputAutomaton<?, I, ?, D>, I, D> {

    private final int minDepth;
    private final int maxDepth;

    /**
     * Constructor.
     *
     * @param sulOracle
     *         interface to the system under learning
     * @param maxDepth
     *         maximum exploration depth
     */
    public CompleteExplorationEQOracle(MembershipOracle<I, D> sulOracle, int maxDepth) {
        this(sulOracle, 1, maxDepth);
    }

    /**
     * Constructor.
     *
     * @param sulOracle
     *         interface to the system under learning
     * @param minDepth
     *         minimum exploration depth
     * @param maxDepth
     *         maximum exploration depth
     */
    public CompleteExplorationEQOracle(MembershipOracle<I, D> sulOracle, int minDepth, int maxDepth) {
        this(sulOracle, 1, minDepth, maxDepth);
    }

    /**
     * Constructor.
     *
     * @param sulOracle
     *         interface to the system under learning
     * @param minDepth
     *         minimum exploration depth
     * @param maxDepth
     *         maximum exploration depth
     * @param batchSize
     *         size of the batches sent to the membership oracle
     */
    public CompleteExplorationEQOracle(MembershipOracle<I, D> sulOracle, int minDepth, int maxDepth, int batchSize) {
        super(sulOracle, batchSize);
        this.minDepth = Math.min(minDepth, maxDepth);
        this.maxDepth = Math.max(minDepth, maxDepth);
    }

    @Override
    protected Stream<Word<I>> generateTestWords(DetOutputAutomaton<?, I, ?, D> hypothesis,
                                                Collection<? extends I> inputs) {
        return Streams.stream(CollectionsUtil.allTuples(inputs, minDepth, maxDepth)).map(Word::fromList);
    }

}
