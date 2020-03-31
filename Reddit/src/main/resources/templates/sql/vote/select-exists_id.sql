select-exists-vote = SELECT EXISTS (
    SELECT v.* FROM votes v WHERE v.applied_id = ? AND v.vote_applicable = ? AND v.voter_id = ?)