package fr.istic.prg1.list;

import fr.istic.prg1.list_util.SmallSet;
import fr.istic.prg1.list_util.SuperT;

/**
 * @author Gaspard KINDJI <chabkind@gmail.com>
 * @author Déborah GELIN <deborahgelin@hotmail.fr>
 * 
 * @version 1.0
 * @since 2019-10-28
 * 
 *        Classe représentant un sous ensemble du grand ensemle MySet
 */

public class SubSet implements SuperT{

	public final int rank;
	public SmallSet set;

	public SubSet() {
		rank = 0;
		set = new SmallSet();
	}

	public SubSet(int r, SmallSet f) {
		rank = r;
		set = f;
	}

	@Override
	public SubSet clone() {
		return new SubSet(rank, set.clone());
	}

	@Override
	public String toString() {
		return "Subset [rank=" + rank + ", set=" + set + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + rank;
		result = prime * result + ((set == null) ? 0 : set.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SubSet)) {
			return false;
		}
		SubSet other = (SubSet) obj;
		return rank == other.rank && set.equals(other.set);
	}
}