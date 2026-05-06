UPDATE public.topics_users
SET domain_level = ROUND(domain_level * 100.0, 2)
WHERE domain_level > 0.0
  AND domain_level <= 1.0;

UPDATE public.topics_users
SET domain_level = 100.0
WHERE domain_level > 100.0;

UPDATE public.topics_users
SET domain_level = 0.0
WHERE domain_level < 0.0;
