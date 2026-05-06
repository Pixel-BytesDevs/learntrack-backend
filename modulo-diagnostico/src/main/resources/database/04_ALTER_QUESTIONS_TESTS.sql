ALTER TABLE public.questions_tests
    ADD COLUMN IF NOT EXISTS attempts INTEGER NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS hints_used INTEGER NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS is_correct BOOLEAN,
    ADD COLUMN IF NOT EXISTS grade_penalized NUMERIC(4,2);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'attempts_check'
          AND conrelid = 'public.questions_tests'::regclass
    ) THEN
        ALTER TABLE public.questions_tests
            ADD CONSTRAINT attempts_check CHECK (attempts BETWEEN 0 AND 4);
    END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'grade_check'
          AND conrelid = 'public.questions_tests'::regclass
    ) THEN
        ALTER TABLE public.questions_tests
            ADD CONSTRAINT grade_check CHECK (grade_penalized BETWEEN 0.0 AND 1.0);
    END IF;
END $$;
