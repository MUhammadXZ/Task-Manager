-- Drop trigger if it exists
DROP TRIGGER IF EXISTS workspace_creation_trigger ON workspaces;

-- Recreate the function and trigger
CREATE OR REPLACE FUNCTION create_task_partition()
RETURNS TRIGGER AS $$
DECLARE
    partition_name TEXT;
BEGIN
    partition_name := 'tasks_ws_' || substr(NEW.id::text, 1, 8);

    IF NOT EXISTS (
        SELECT FROM pg_tables
        WHERE tablename = partition_name
    ) THEN
        EXECUTE format(
            'CREATE TABLE %I PARTITION OF tasks '
            'FOR VALUES IN (%L)',
            partition_name,
            NEW.id
        );
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER workspace_creation_trigger
AFTER INSERT ON workspaces
FOR EACH ROW EXECUTE FUNCTION create_task_partition();