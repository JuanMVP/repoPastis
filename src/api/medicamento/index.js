import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { create, index, show, update, destroy } from './controller'
import { schema } from './model'
import { token } from '../../services/passport'
export Medicamento, { schema } from './model'

const router = new Router()
const { nombre, cantidad, gramos } = schema.tree

/**
 * @api {post} /medicamentos Create medicamento
 * @apiName CreateMedicamento
 * @apiGroup Medicamento
 * @apiParam nombre Medicamento's nombre.
 * @apiParam cantidad Medicamento's cantidad.
 * @apiParam gramos Medicamento's gramos.
 * @apiSuccess {Object} medicamento Medicamento's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Medicamento not found.
 */
router.post('/',
  token({required: true, roles:['admin','user']}),
  body({ nombre, cantidad, gramos }),
  create)

/**
 * @api {get} /medicamentos Retrieve medicamentos
 * @apiName RetrieveMedicamentos
 * @apiGroup Medicamento
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of medicamentos.
 * @apiSuccess {Object[]} rows List of medicamentos.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  token({required: true, roles:['admin','user']}),
  query(),
  index)

/**
 * @api {get} /medicamentos/:id Retrieve medicamento
 * @apiName RetrieveMedicamento
 * @apiGroup Medicamento
 * @apiSuccess {Object} medicamento Medicamento's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Medicamento not found.
 */
router.get('/:id',
  token({required: true, roles:['admin','user']}),
  show)

/**
 * @api {put} /medicamentos/:id Update medicamento
 * @apiName UpdateMedicamento
 * @apiGroup Medicamento
 * @apiParam nombre Medicamento's nombre.
 * @apiParam cantidad Medicamento's cantidad.
 * @apiParam gramos Medicamento's gramos.
 * @apiSuccess {Object} medicamento Medicamento's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Medicamento not found.
 */
router.put('/:id',
  token({required: true, roles:['admin','user']}),
  body({ nombre, cantidad, gramos }),
  update)

/**
 * @api {delete} /medicamentos/:id Delete medicamento
 * @apiName DeleteMedicamento
 * @apiGroup Medicamento
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Medicamento not found.
 */
router.delete('/:id',
  token({required: true, roles:['admin','user']}),
  destroy)

export default router
