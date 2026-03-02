
package entity;
/**
 *
 * @author CHANG JIA JIN
 */
public class SearchResult {
        private final Search job;
        private final int rank;

        public SearchResult(Search job, int rank) {
            this.job = job;
            this.rank = rank;
        }

        public Search getJob() {
            return job;
        }

        public int getRank() {
            return rank;
        }
}
